package domain.repository;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.entity.Member;

public class MemberRepository {
	
	private static Long nextId = 0L;

	private Map<Long, Member> map = new HashMap<>();
	
	public void save(Member member) {
		member.setId(++nextId);
		map.put(member.getId(), member);
	}
	public Member findOne(Long id) {
		return map.get(id);
	}
	public Collection<Member> findAll() {
		return map.values();
	}
	public List<Member> findByName(String name) {
		return Collections.emptyList();
	}

}
