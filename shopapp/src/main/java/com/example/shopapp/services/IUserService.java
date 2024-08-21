package com.example.shopapp.services;

import com.example.shopapp.dtos.UserDTO;
import com.example.shopapp.exceptions.DataNotFoundException;
import com.example.shopapp.exceptions.PermissionDenyException;
import com.example.shopapp.models.User;

public interface IUserService {
    User register(UserDTO userDTO) throws DataNotFoundException, PermissionDenyException;
    String login(String phoneNumber, String password) throws DataNotFoundException;
}
