package com.agl.daily_news.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.agl.daily_news.model.User;
import com.agl.daily_news.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UserRepository userRepository;

  /*
   * username ini sebagai email pada saat login
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    /*
     * findbyemail untuk cari username sebagai email ada usernya atau tidak
     * kalau ada, kita build userdetails yang nantinya akn dibuat untuk security
     * context holder
     * kalau tidak, kita throw user not found
     */
    if (!userRepository.existsByEmail(username)) {
      throw new UsernameNotFoundException(username + " is not found!");
    }

    User user = userRepository.findByEmail(username);
    if (user == null) {
      throw new UsernameNotFoundException("User not found with email: " + username);
  }
    // validasi dari obj user atau creator atau admin mana yg ngga null
    // yg ngga null adalah admin -> role = "ROLE_ADMIN"

    return UserDetailsImpl.buid(user);
  }

}
