package com.brand13.authtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brand13.authtest.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
    
    Member findByUsername(String username);
    
}
