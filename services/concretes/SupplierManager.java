package com.etiya.ecommerce.services.concretes;

import com.etiya.ecommerce.core.exception.types.NotFoundException;
import com.etiya.ecommerce.core.internationalization.MessageService;
import com.etiya.ecommerce.core.utils.mapping.ModelMapperService;
import com.etiya.ecommerce.core.utils.result.DataResult;
import com.etiya.ecommerce.core.utils.result.SuccessDataResult;
import com.etiya.ecommerce.entities.concrete.Supplier;
import com.etiya.ecommerce.repositories.abstracts.ISupplierDao;
import com.etiya.ecommerce.services.abstracts.SupplierService;
import com.etiya.ecommerce.services.constans.Messages;
import com.etiya.ecommerce.services.dtos.requests.supplier.AddSupplierRequest;
import com.etiya.ecommerce.services.dtos.responses.supplier.AddSupplierResponse;
import com.etiya.ecommerce.services.dtos.responses.supplier.GetByIdSupplierResponse;
import com.etiya.ecommerce.services.dtos.responses.supplier.ListSupplierResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SupplierManager implements SupplierService {

    private final ISupplierDao supplierDao;
    private final ModelMapperService mapperService;
    private final MessageService messageService;

    @Override
    public DataResult<GetByIdSupplierResponse> getById(Integer id) {

        Supplier supplier = supplierDao.findById(id).orElseThrow(
                () -> new NotFoundException(messageService.getMessageWithParams(Messages.Supplier.supplierNotFound, id))
        );

        GetByIdSupplierResponse response = mapperService.forResponse().map(supplier, GetByIdSupplierResponse.class);

        return new SuccessDataResult<>(response, Messages.Supplier.supplierWithId);
    }

    @Override
    public DataResult<AddSupplierResponse> add(AddSupplierRequest addSupplierRequest) {

        Supplier supplier = mapperService.forRequest().map(addSupplierRequest, Supplier.class);
        supplierDao.save(supplier);

        AddSupplierResponse response = mapperService.forResponse().map(addSupplierRequest, AddSupplierResponse.class);
        return new SuccessDataResult<>(response, Messages.Supplier.supplierAdd);
    }

    @Override
    public DataResult<List<ListSupplierResponse>> getAll() {
        List<ListSupplierResponse> responses = supplierDao.getAll();
        return new SuccessDataResult<>(responses,messageService.getMessage(Messages.Supplier.supplierListed));
    }

    @Override
    public DataResult<Slice<ListSupplierResponse>> getWithPagination(Pageable pageable) {
        return new SuccessDataResult<>(supplierDao.getAll(pageable));
    }
}
