package com.tintoll.admin.ifs;

import com.tintoll.admin.model.network.Header;
import com.tintoll.admin.model.network.request.UserApiRequest;
import com.tintoll.admin.model.network.response.UserApiResponse;

public interface CrudInterface {

    Header<UserApiResponse> create(Header<UserApiRequest> userApiRequest);

    Header<UserApiResponse> read(Long id);

    Header<UserApiResponse> update(Header<UserApiRequest> userApiRequest);

    Header<UserApiResponse> delete(Long id);

}
