package sasci.spring;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.*;
import org.springframework.web.bind.annotation.*;

// We can use a number of different clients. For the lab, we'll use Feign.
// For simplicity, we'll just use Map to represent the entities.
// We'll default the endpoint to localhost for now, this will be overridden.
@FeignClient(value = "messages", url = "${messages.endpoint:http://localhost:8081/guestbookMessages}")
public interface GuestbookMessagesClient {
	@RequestMapping(method = RequestMethod.GET, path = "/")
	Resources<Map> getMessages();

	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	Map getMessage(@PathVariable("id") long messageId);

	@RequestMapping(method = RequestMethod.POST, path = "/")
	Resource<Map> add(@RequestBody Map message);
}
