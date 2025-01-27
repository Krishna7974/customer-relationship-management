package in.sk.main.api;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import in.sk.main.entities.Orders;
import in.sk.main.services.OrdersService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OrdersApi {

    @Autowired
    private OrdersService ordersService;

//    @PostMapping("/storeOrderDetails")
//    public ResponseEntity<String> storeUserOrderDetails(@RequestBody Orders orders){
//        ordersService.storeUserOrder(orders);
//        return ResponseEntity.ok("Order added Successfully");
//    }

    @PostMapping("/storeOrderDetails")
    public ResponseEntity<String> storeUserOrderDetails(@RequestBody Orders orders) throws RazorpayException {
        RazorpayClient razorpayClient=new RazorpayClient("rzp_test_4FQSRT6mvPVmRX","CUutzSwb18Mkp2gRfYCQdwnu");

        JSONObject orderRequest=new JSONObject();
        orderRequest.put("amount",orders.getCourseAmount());
        orderRequest.put("currency","INR");
        orderRequest.put("receipt", "receipt_id_" + System.currentTimeMillis());

        Order order=razorpayClient.orders.create(orderRequest);

        String orderID=order.get("id");
        orders.setOrderId(orderID);

        ordersService.storeUserOrder(orders);
        return ResponseEntity.ok("Order added Successfully");
    }
}
