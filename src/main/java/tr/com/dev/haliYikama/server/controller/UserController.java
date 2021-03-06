package tr.com.dev.haliYikama.server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.com.dev.haliYikama.server.persist.models.User;
import tr.com.dev.haliYikama.server.utils.BaseController;

/**
 * Created by ramazancesur on 7/22/18.
 */
@RestController
@RequestMapping(value = "/user")
public class UserController extends BaseController<User> {
}
