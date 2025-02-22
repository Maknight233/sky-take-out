package com.sky.controller.user;

import com.sky.entity.AddressBook;
import com.sky.result.Result;
import com.sky.service.AddressBookService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/addressBook")
@Slf4j
@Api(tags = "address book")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    @PostMapping
    public Result<String> add(@RequestBody AddressBook addressBook) {
        addressBookService.add(addressBook);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<List<AddressBook>> getCurrentUserAddress() {
        return Result.success(addressBookService.getCurrentUserAddress());
    }

    @GetMapping("/default")
    public Result<AddressBook> getDefaultAddress(){
        return Result.success(addressBookService.getDefaultAddress());
    }

    @PutMapping("/default")
    public Result<String> setDefaultAddress(@RequestBody AddressBook addressBook){
        addressBook.setIsDefault(1);
        addressBookService.update(addressBook);
        return Result.success();
    }

    @PutMapping
    public Result<String> update(@RequestBody AddressBook addressBook) {
        addressBookService.update(addressBook);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<AddressBook> getById(@PathVariable Long id) {
        AddressBook addressBook = addressBookService.getById(id);
        return Result.success(addressBook);
    }

    @DeleteMapping
    public Result<String> delete(Long id) {
        addressBookService.deleteById(id);
        return Result.success();
    }
}
