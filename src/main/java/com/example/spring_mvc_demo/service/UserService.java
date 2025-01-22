package com.example.spring_mvc_demo.service;

import com.example.spring_mvc_demo.dto.LoginDTO;
import com.example.spring_mvc_demo.dto.UserDTO;
import com.example.spring_mvc_demo.dto.UserUpdateRequest;
import com.example.spring_mvc_demo.dto.response.LoginResponse;
import com.example.spring_mvc_demo.dto.response.UserListResponse;
import com.example.spring_mvc_demo.dto.response.UserResponse;
import com.example.spring_mvc_demo.helper.exception.AppException;
import com.example.spring_mvc_demo.helper.exception.ErrorCode;
import com.example.spring_mvc_demo.model.Department;
import com.example.spring_mvc_demo.model.User;
import com.example.spring_mvc_demo.repository.DepartmentRepository;
import com.example.spring_mvc_demo.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    DepartmentRepository departmentRepository;
    PlatformTransactionManager transactionManager;
    ModelMapper modelMapper;
    SessionFactory sessionFactory;
    static String URL = "jdbc:oracle:thin:@//localhost:1521/orclpdb";
    static String USER = "common";
    static String PASSWORD = "common";

    public User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    public User convertToUserUpdate(UserUpdateRequest userUpdateRequest) {
        return modelMapper.map(userUpdateRequest, User.class);
    }

    public List<User> getAllUserV2() {
        List<User> users = new ArrayList<>();
        String query = "SELECT USER_ID, USERNAME, EMAIL, PHONE_NUMBER, FIRST_NAME, LAST_NAME, DEPARTMENT_ID, STATUS FROM COMMON.USERS";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            connection.setAutoCommit(false);

            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    User user = new User();
                    user.setUserId(resultSet.getLong("USER_ID"));
                    user.setUsername(resultSet.getString("USERNAME"));
                    user.setEmail(resultSet.getString("EMAIL"));
                    user.setPhoneNumber(resultSet.getString("PHONE_NUMBER"));
                    user.setFirstName(resultSet.getString("FIRST_NAME"));
                    user.setLastName(resultSet.getString("LAST_NAME"));
                    user.setStatus(resultSet.getInt("STATUS"));
                    users.add(user);
                }

                connection.commit();

            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
                throw new RuntimeException("Error while fetching users", e);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database connection error", e);
        }

        return users;
    }

    @Transactional(readOnly = true)
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<User> getAllUserSF() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM User";
            Query<User> query = session.createQuery(hql, User.class);
            List<User> users = query.list();
            return users;
        }
    }

    public Page<UserListResponse> getAllUserHQL(int page, int size, String sortBy, String direction){
        Sort sort = direction.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<User> usersPage = userRepository.findAllUsersHQL(pageable);

        Page<UserListResponse> userListResponse = usersPage.map(user ->
                new UserListResponse(
                        user.getUserId(),
                        user.getEmail(),
                        user.getPhoneNumber(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getDepartment().getDepartmentId(),
                        user.getStatus()
                )
        );

        return userListResponse;
    }

//    public Page<User> getUsersByName(String name, int page, int size, String sortBy, String direction) {
//        Sort sort = direction.equalsIgnoreCase(Sort.Direction.ASC.name())
//                ? Sort.by(sortBy).ascending()
//                : Sort.by(sortBy).descending();
//        Pageable pageable = PageRequest.of(page, size, sort);
//        return userRepository.filterByName(name, pageable);
//    }

    public List<User> filterUser(String name){
        return userRepository.filterByName(name);
    }

    public List<UserListResponse> filterUserByName(String name) {
        List<User> users = userRepository.filterByName(name);

        return users.stream()
                .map(user -> new UserListResponse(
                        user.getUserId(),
                        user.getEmail(),
                        user.getPhoneNumber(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getDepartment().getDepartmentId(),
                        user.getStatus()
                ))
                .collect(Collectors.toList());
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow();
    }
    @Transactional
    public User addUser1(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username đã tồn tại. Vui lòng chọn username khác.");
        }
        return userRepository.save(user);
    }


    public User addUser2(User user) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("AddUserTransaction");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus status = transactionManager.getTransaction(def);

        try {
            if (userRepository.existsByUsername(user.getUsername())) {
                throw new IllegalArgumentException("Username đã tồn tại. Vui lòng chọn username khác.");
            }

            User savedUser = userRepository.save(user);

            transactionManager.commit(status);
            return savedUser;

        } catch (Exception ex) {
            transactionManager.rollback(status);
            throw new RuntimeException("Giao dịch thất bại: " + ex.getMessage(), ex);
        }
    }

    public UserResponse viewUserDetail(Long userId) {
        User user = userRepository.findUserByUserId(userId);

        if (user == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        UserResponse response = new UserResponse();
        response.setUserId(user.getUserId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setDepartmentId(user.getDepartment().getDepartmentId());
        response.setStatus(user.getStatus());
        response.setCreatedTime(user.getCreatedTime());
        response.setUpdatedTime(user.getUpdatedTime());
        response.setCreatedUser(user.getCreatedUser());
        response.setUpdatedUser(user.getUpdatedUser());

        return response;
    }


    public void addUserJSON(UserDTO userDTO) {

        User user = userRepository.findByUsername(userDTO.getUsername());
        if (user != null) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        boolean existed = userRepository.existsByEmailAndPhoneNumber(userDTO.getEmail(), userDTO.getPhoneNumber());
        if (existed) {
            throw new AppException(ErrorCode.EMAIL_OR_PHONENUMBER_EXISTED);
        }
        Department department = departmentRepository.findDepartmentById(userDTO.getDepartmentId());
        if (department == null) {
            throw new AppException(ErrorCode.DEPARTMENT_NOT_EXISTED);
        }

        userRepository.save(User.builder()
                .email(userDTO.getEmail())
                .phoneNumber(userDTO.getPhoneNumber())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .department(department)
                .status(1)
                .createdTime(new Date())
                .createdUser(1L)
                .build());
    }

    public void updateUser(Long userId, UserUpdateRequest userUpdateRequest) {

        User user = userRepository.findUserByUserId(userId);
        if (user == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        if (!user.getEmail().equals(userUpdateRequest.getEmail())) {
            user.setEmail(userUpdateRequest.getEmail());
        }
        if (!user.getPhoneNumber().equals(userUpdateRequest.getPhoneNumber())) {
            user.setPhoneNumber(userUpdateRequest.getPhoneNumber());
        }
        user.setFirstName(userUpdateRequest.getFirstName());
        user.setLastName(userUpdateRequest.getLastName());
        user.setUpdatedTime(new Date());
        user.setUpdatedUser(1L);
        userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findUserByUserId(userId);
        if (user == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        userRepository.delete(user);
    }

    public LoginResponse auth(LoginDTO loginDTO) {
        User user = userRepository.findByUsername(loginDTO.getUsername());
        boolean isAuth = true;
        if (user == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }

        User authenticated = userRepository.findByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword());
        if (authenticated == null) {
            isAuth = false;
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        return LoginResponse.builder()
                .isAuth(isAuth)
                .build();
    }

}
