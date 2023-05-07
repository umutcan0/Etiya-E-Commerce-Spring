package com.etiya.ecommerce.services.concretes;

import com.etiya.ecommerce.core.exception.types.NotFoundException;
import com.etiya.ecommerce.core.internationalization.MessageManager;
import com.etiya.ecommerce.core.internationalization.MessageService;
import com.etiya.ecommerce.core.utils.mapping.ModelMapperManager;
import com.etiya.ecommerce.core.utils.mapping.ModelMapperService;
import com.etiya.ecommerce.core.utils.result.DataResult;
import com.etiya.ecommerce.core.utils.result.SuccessDataResult;
import com.etiya.ecommerce.entities.concrete.Address;
import com.etiya.ecommerce.repositories.abstracts.AddressDao;
import com.etiya.ecommerce.services.constans.Messages;
import com.etiya.ecommerce.services.dtos.requests.address.AddAddressRequest;
import com.etiya.ecommerce.services.dtos.responses.address.AddAddressResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AddressManagerTest {

     AddressDao addressDao;

     ModelMapperService mapperService;

    MessageManager messageService;

     AddressManager addressManager;


    @BeforeEach
    void setUp() {
        addressDao=mock(AddressDao.class);

        mapperService=new ModelMapperManager(new ModelMapper());

        messageService  =  new MessageManager(bundleMessageSource());

        addressManager=new AddressManager(addressDao,mapperService,messageService);
    }

    ResourceBundleMessageSource bundleMessageSource(){
        ResourceBundleMessageSource messageSource=new ResourceBundleMessageSource();
        messageSource.setBasename("message");
        return messageSource;
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addAddress(){
        AddAddressRequest request=AddAddressRequest.builder()
                .address("Test-Adres")
                .city("Test-City")
                .country("Test-Country")
                .countryCode("Test-CountryCode")
                .details("Test-Details")
                .postcode(454241)
                .build();

        Address address = mapperService.forRequest().map(request, Address.class);
        addressDao.save(address);

        AddAddressResponse addressResponse = mapperService.forResponse().map(address, AddAddressResponse.class);


        DataResult<AddAddressResponse>actualResponse=addressManager.addAddress(request);
        when(addressManager.addAddress(request)).thenReturn(actualResponse);

        DataResult<AddAddressResponse>dataResult=new SuccessDataResult<>(addressResponse, messageService.getMessage(Messages.Address.addressAdd));


        Assertions.assertEquals(dataResult.getData(),actualResponse.getData());
    }
    @Test
    void getByIdWithNonExistingIdShouldThrowException(){

        when(addressDao.findById(any())).thenReturn(Optional.ofNullable(null));
        int id=2;
        Assertions.assertThrows(NotFoundException.class,()->{
            addressManager.addressGetById(id);
        });
    }
    }

