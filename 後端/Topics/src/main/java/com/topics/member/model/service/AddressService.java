package com.topics.member.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.topics.member.model.dto.AddressDto;
import com.topics.member.model.entity.AddressBean;
import com.topics.member.model.entity.MemberBean;
import com.topics.member.model.repository.AddressRepository;
import com.topics.member.model.repository.MemberRepository;

@Service
public class AddressService {

	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	public AddressDto insertAddress(AddressBean address, Integer memberId) {
		Optional<MemberBean> memberOp = memberRepository.findById(memberId);
		if(memberOp.isPresent()) {
			address.setMember(memberOp.get());
			AddressBean addressNew = addressRepository.save(address);
			return new AddressDto(addressNew);
		}
		return null;
	}
	
	public AddressDto updateAddress(AddressBean address, Integer id) {
		Optional<AddressBean> AddressOp = addressRepository.findById(id);
		AddressBean addressNew = AddressOp.get();
		addressNew.setRecipientName(address.getRecipientName());
		addressNew.setPhone(address.getPhone());
		addressNew.setZipcode(address.getZipcode());
		addressNew.setCity(address.getCity().replace("台", "臺"));
		addressNew.setDistrict(address.getDistrict().replace("台", "臺"));
		addressNew.setAddressDetail(address.getAddressDetail().replace("台", "臺"));
		addressNew.setDefaultStatus(address.isDefaultStatus());
		AddressBean save = addressRepository.save(addressNew);
		return new AddressDto(save);
	}
	
//	public AddressDto deleteAddressById(Integer id) {
//		addressRepository.findById(id);
//	}

	public AddressDto findById(Integer id) {
		Optional<AddressBean> op = addressRepository.findById(id);
		if(op.isPresent()) {
			return new AddressDto(op.get());
		}
		return null;
	}
	
	public List<AddressDto> findAddressById(Integer memberId) {
		List<AddressDto> address = addressRepository.findAddressById(memberId).stream()
				.map(a -> new AddressDto(a))
				.toList();
		return address;
	}
	
}
