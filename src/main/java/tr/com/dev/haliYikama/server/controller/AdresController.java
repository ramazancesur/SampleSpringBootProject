package tr.com.dev.haliYikama.server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.com.dev.haliYikama.server.persist.models.Adres;
import tr.com.dev.haliYikama.server.utils.BaseController;

@RestController
@RequestMapping(value = "/address")
public class AdresController extends BaseController<Adres> {

}
