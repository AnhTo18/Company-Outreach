package org.springframework.samples.outreach.PayPalControllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("demo")
public class CartController {

		@RequestMapping(method = RequestMethod.GET)
		public String index() {
			return "cart/index";
		}
}
