package com.brand13.authtest.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.brand13.authtest.domain.Member;
import com.brand13.authtest.repository.MemberRepository;

@Service
public class LoginUserDetailsService implements UserDetailsService{

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Member memberEntity = memberRepository.findByUsername(username);

        if(memberEntity != null){
            return new LoginUserDetails(memberEntity);
        }

        return null;
    }
}
