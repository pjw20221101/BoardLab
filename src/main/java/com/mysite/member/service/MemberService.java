package com.mysite.member.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mysite.constant.MemberRole;
import com.mysite.member.Member;
import com.mysite.member.dto.MemberFormDto;
import com.mysite.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService{
	
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	
	public void save(MemberFormDto memberFormDto) {
		
		Member member = new Member();
		
		member.setMName(memberFormDto.getMName());
		member.setMPassword(this.passwordEncoder.encode(memberFormDto.getMPassword()));
		member.setEmail(memberFormDto.getEmail());
		member.setAddr(memberFormDto.getAddr());
		
		this.memberRepository.save(member);
		
	}
	
	@Override
public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		System.out.println(email);
		
		Optional<Member> _Member=this.memberRepository.findByEmail(email);
		
		if(_Member.isEmpty()) {
			
			System.out.println("비어있음");
			throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
			
		}
		
		Member member = _Member.get();
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		if("admin".equals(email)) {
			authorities.add(new SimpleGrantedAuthority(MemberRole.ADMIN.getValue()));
		}else {
			authorities.add(new SimpleGrantedAuthority(MemberRole.USER.getValue()));
		}
		
		return new User(member.getEmail(),member.getMPassword(),authorities);
	}
	
}
