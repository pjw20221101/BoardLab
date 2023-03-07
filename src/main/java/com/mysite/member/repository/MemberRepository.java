package com.mysite.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.member.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> {
	
	Optional<Member> findByEmail(String email);
	
}
