package quick.pager.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quick.pager.shop.mapper.OrderTradeMapper;
import quick.pager.shop.model.OrderTrade;
import quick.pager.shop.order.request.OrderTradeSaveRequest;
import quick.pager.shop.service.OrderTradeService;
import quick.pager.shop.user.response.Response;
import quick.pager.shop.utils.DateUtils;

import java.math.BigDecimal;

@Service
public class OrderTradeServiceImpl implements OrderTradeService {

    @Autowired
    private OrderTradeMapper orderTradeMapper;

    /**
     * Creates a new order trade based on the provided request.
     *
     * @param request The order trade save request containing trade details
     * @return A response containing the ID of the newly created order trade
     * @throws RuntimeException if the request fails validation (invalid userId or totalAmount)
     */
    @Override
    public Response<Long> create(final OrderTradeSaveRequest request) {

        validate(request);
        OrderTrade orderTrade = new OrderTrade();
        orderTrade.setUserId(request.getUserId());
        orderTrade.setOutTradeNo(request.getOutTradeNo());
        orderTrade.setTradeNo(request.getTradeNo());
        orderTrade.setPayType(request.getPayType().getCode());
        orderTrade.setTradeType(request.getTradeType().getCode());
        orderTrade.setTotalAmount(request.getTotalAmount());
        orderTrade.setCreateTime(DateUtils.dateTime());
        orderTrade.setUpdateTime(DateUtils.dateTime());
        orderTrade.setDeleteStatus(Boolean.FALSE);
        orderTradeMapper.insert(orderTrade);
        return Response.toResponse(orderTrade.getId());
    }

    /**
     * Validates the order trade save request by checking the user ID and total amount.
     *
     * @param request The order trade save request to validate
     * @throws RuntimeException if the user ID is less than or equal to zero
     * @throws RuntimeException if the total amount is less than or equal to zero
     */
    private void validate(OrderTradeSaveRequest request) {
        if(request.getUserId() <= 0){
            throw new RuntimeException("userId illegal");
        }

        if(request.getTotalAmount().compareTo(BigDecimal.ZERO) <= 0){
            throw new RuntimeException("totalAmount illegal");
        }
    }
}
