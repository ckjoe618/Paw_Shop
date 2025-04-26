package com.topics.member.model.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.topics.exception.NotFoundException;
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
		MemberBean member = memberRepository.findById(memberId)
				.orElseThrow(() -> new NotFoundException("找不到對應的會員"));
		address.setMember(member);
		AddressBean saved = addressRepository.save(address);
		return new AddressDto(saved);
	}

	public AddressDto updateAddressById(Integer id, AddressBean address) {
		AddressBean addressNew = addressRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("找不到該地址"));

		addressNew.setRecipientName(address.getRecipientName());
		addressNew.setPhone(address.getPhone());
		addressNew.setZipcode(address.getZipcode());
		addressNew.setCity(address.getCity().replace("台", "臺"));
		addressNew.setDistrict(address.getDistrict().replace("台", "臺"));
		addressNew.setAddressDetail(address.getAddressDetail().replace("台", "臺"));
//		addressNew.setDefaultStatus(address.isDefaultStatus());
		AddressBean save = addressRepository.save(addressNew);
		return new AddressDto(save);
	}

	public AddressDto deleteAddressById(Integer id) {
		AddressBean address = addressRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("找不到該地址"));
		address.setActiveStatus(false);
		AddressBean save = addressRepository.save(address);
		return new AddressDto(save);
	}

	public AddressDto findAddressById(Integer id) {
		AddressBean address = addressRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("找不到該地址"));
		return new AddressDto(address);
	}

	public List<AddressDto> findAddressByMemberId(Integer memberId) {
		List<AddressDto> address = addressRepository.findAddressByMemberId(memberId).stream()
				.map(a -> new AddressDto(a))
				.toList();
		return address;
	}

}
