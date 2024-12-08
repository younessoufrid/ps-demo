package com.demo.portailsaisie.backend.core.service.v1;

import com.demo.portailsaisie.backend.core.domain.User;

import com.demo.portailsaisie.backend.core.dto.user.UserRequestDto;
import com.demo.portailsaisie.backend.core.dto.user.UserResponseDto;
import com.demo.portailsaisie.backend.core.mapping.UserMapper;
import com.demo.portailsaisie.backend.core.repository.UserRepository;
import com.demo.portailsaisie.backend.core.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper mapper;

    /**** Example of manipulation with mapstruct for layers separation **/
    /**** To Update in Users manipulation tickets**/
    public UserResponseDto create(UserRequestDto user){
        return mapper.map(                  // map from User to UserResponseDto
                userRepository.save(
                        mapper.map(user)    // map from UserRequestDto to User
                )
        );
    }

    @Override
    public boolean isExistsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
