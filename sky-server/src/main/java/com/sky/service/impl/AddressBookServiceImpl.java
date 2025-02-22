package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.AddressBookMapper;
import com.sky.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AddressBookServiceImpl implements AddressBookService {

    @Autowired
    private AddressBookMapper addressBookMapper;

    @Override
    public void add(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBookMapper.add(addressBook);
    }

    @Override
    public List<AddressBook> getCurrentUserAddress() {
        return addressBookMapper.getByUserId(BaseContext.getCurrentId());
    }

    @Override
    public AddressBook getDefaultAddress() {
        return addressBookMapper.getDefaultByUserId(BaseContext.getCurrentId());
    }

    @Override
    public void update(AddressBook addressBook) {
        //need to change default status
        if(addressBook.getIsDefault() != null){
            //need to set default status
            if(addressBook.getIsDefault() == 1){
                //check if current user have default address
                AddressBook defaultAddressBook = addressBookMapper.getDefaultByUserId(BaseContext.getCurrentId());
                if(defaultAddressBook != null){
                    //if yes, set it to 0(not default)
                    addressBookMapper.setDefaultById(defaultAddressBook.getId(), 0);
                }
            }
        }
        addressBookMapper.update(addressBook);
    }

    @Override
    public AddressBook getById(Long id) {
        return addressBookMapper.getById(id);
    }

    @Override
    public void deleteById(Long id) {
        AddressBook addressBook = addressBookMapper.getById(id);
        if(addressBook.getIsDefault() != 1) {
            addressBookMapper.deleteById(id);
        } else {
            throw new DeletionNotAllowedException("Cannot delete default address book");
        }
    }
}
