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

	public AddressDto insertAddress(AddressDto address) {
		MemberBean member = memberRepository.findById(address.getMemberId())
				.orElseThrow(() -> new NotFoundException("找不到對應的會員"));
		AddressBean addressNew = new AddressBean();
		addressNew.setRecipientName(address.getRecipientName());
		addressNew.setPhone(address.getPhone());
		addressNew.setZipcode(address.getZipcode());
		addressNew.setCity(address.getCity());
		addressNew.setDistrict(address.getDistrict());
		addressNew.setAddressDetail(address.getAddressDetail());
		addressNew.setMember(member);
		AddressBean save = addressRepository.save(addressNew);
		return new AddressDto(save);
	}

	public AddressDto updateAddressByAddressId(AddressBean address) {
		AddressBean addressNew = addressRepository.findById(address.getAddressId())
				.orElseThrow(() -> new NotFoundException("找不到該地址"));

		addressNew.setRecipientName(address.getRecipientName());
		addressNew.setPhone(address.getPhone());
		addressNew.setZipcode(address.getZipcode());
		addressNew.setCity(address.getCity().replace("台", "臺"));
		addressNew.setDistrict(address.getDistrict().replace("台", "臺"));
		addressNew.setAddressDetail(address.getAddressDetail().replace("台", "臺"));
		AddressBean save = addressRepository.save(addressNew);
		return new AddressDto(save);
	}

	public void updateDefaultAddress(Integer addressId) {
		AddressBean addressNew = addressRepository.findById(addressId)
				.orElseThrow(() -> new NotFoundException("找不到該地址"));

		Integer memberId = addressNew.getMember().getMemberId();
		addressRepository.clearDefaultByMemberId(memberId);
		addressNew.setDefaultStatus(true);
		addressRepository.save(addressNew);
	}

	public AddressDto deleteAddressById(Integer addressId) {
		AddressBean address = addressRepository.findById(addressId)
				.orElseThrow(() -> new NotFoundException("找不到該地址"));
		address.setActiveStatus(false);
		AddressBean save = addressRepository.save(address);
		return new AddressDto(save);
	}

	public List<AddressDto> findAddressByMemberId(Integer memberId) {
		List<AddressDto> address = addressRepository.findAddressByMemberId(memberId).stream()
				.map(a -> new AddressDto(a))
				.toList();
		return address;
	}

}
