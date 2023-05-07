package com.etiya.ecommerce.services.concretes;

import com.etiya.ecommerce.core.exception.types.NotFoundException;
import com.etiya.ecommerce.core.internationalization.MessageManager;
import com.etiya.ecommerce.core.internationalization.MessageService;
import com.etiya.ecommerce.core.utils.mapping.ModelMapperService;
import com.etiya.ecommerce.core.utils.result.DataResult;
import com.etiya.ecommerce.core.utils.result.SuccessDataResult;
import com.etiya.ecommerce.entities.concrete.Address;
import com.etiya.ecommerce.repositories.abstracts.AddressDao;
import com.etiya.ecommerce.services.abstracts.AddressService;
import com.etiya.ecommerce.services.constans.Messages;
import com.etiya.ecommerce.services.dtos.requests.address.AddAddressRequest;
import com.etiya.ecommerce.services.dtos.responses.address.AddAddressResponse;
import com.etiya.ecommerce.services.dtos.responses.address.GetByIdAddressResponse;
import com.etiya.ecommerce.services.dtos.responses.address.ListAddressResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.context.i18n.LocaleContextHolder.*;

@Service
@AllArgsConstructor
public class AddressManager implements AddressService {


    private final AddressDao addressDao;
    private final ModelMapperService mapperService;
    private final MessageManager messageService;



    @Override
    public DataResult<GetByIdAddressResponse> addressGetById(Integer id) {
        Address address = addressDao.findById(id).orElseThrow
                (()-> new NotFoundException(messageService.getMessageWithParams(Messages.Address.addressNotFound,id, getLocale())));

        GetByIdAddressResponse response = mapperService.forResponse().map(address, GetByIdAddressResponse.class);
        return new SuccessDataResult<>(response, messageService.getMessage(Messages.Address.addressWithId));
    }

    @Override
    public DataResult<AddAddressResponse> addAddress(AddAddressRequest addAddressRequest) {

        Address address = mapperService.forRequest().map(addAddressRequest, Address.class);
        addressDao.save(address);

        AddAddressResponse addressResponse = mapperService.forResponse().map(address, AddAddressResponse.class);
        return new SuccessDataResult<>(addressResponse, messageService.getMessage(Messages.Address.addressAdd));
    }

    @Override
    public DataResult<List<ListAddressResponse>> getAll() {

        return new SuccessDataResult<List<ListAddressResponse>>(addressDao.getAll(),
                messageService.getMessage(Messages.Address.ListedAddress));
    }

    @Override
    public DataResult<Slice<ListAddressResponse>> getWithPagination(Pageable pageable) {
        return new SuccessDataResult<>(addressDao.getAll(pageable),messageService.getMessage(Messages.Address.ListedAddress));
    }


}
