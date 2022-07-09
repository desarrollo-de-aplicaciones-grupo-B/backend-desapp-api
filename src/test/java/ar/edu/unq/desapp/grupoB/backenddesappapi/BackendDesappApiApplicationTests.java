package ar.edu.unq.desapp.grupoB.backenddesappapi;

import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Cotization;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Utils.Exceptions.OutOfRangeCotizationException;
import ar.edu.unq.desapp.grupoB.backenddesappapi.services.CotizationService;
import ar.edu.unq.desapp.grupoB.backenddesappapi.services.CryptocurrencyService;
import ar.edu.unq.desapp.grupoB.backenddesappapi.services.TradingService;
import ar.edu.unq.desapp.grupoB.backenddesappapi.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class BackendDesappApiApplicationTests {
	@Autowired
	private UserService uService;
	@Autowired
	private CotizationService cService;
	@Autowired
	private CryptocurrencyService ccService;
	@Autowired
	private TradingService tService;

	/*@Test
	void contextLoads() throws OutOfRangeCotizationException, UserAlreadyExists {
		Cryptocurrency crypto = new Cryptocurrency();
		crypto.setCryptoName("testusd");
		crypto.setNomenclature("nomenclat");
		crypto.setId(1);
		ccService.save(crypto);

		Cotization cot = new Cotization();
		cot.setId(1);
		cot.setDateCotization(LocalDateTime.now());
		cot.setPriceCotization(12.0);
		cService.save(cot);

		User user1 = new User();
		user1.setId(1);
		user1.setName("uno");
		user1.setAddress("sdad");
		user1.setUserWallet("wallet1");
		user1.setCvu("cbvqd");
		user1.setEmail("dsads@gmail.com");
		user1.setPassword("pass1");
		user1.setReputation(0);
		user1.setSuccessfulOperations(0);
		user1.setLastname("lastname");
		uService.save(user1);

		User user2 = new User();
		user2.setId(2);
		user2.setName("dos");
		user2.setAddress("sdad");
		user2.setUserWallet("wallet1");
		user2.setCvu("cbvqd");
		user2.setEmail("dsads@gmail.com");
		user2.setPassword("pass1");
		user2.setReputation(0);
		user2.setSuccessfulOperations(0);
		user2.setLastname("lastname");
		uService.save(user2);


		//uService.openTrading(1, 1,45.0,11.9, 2500.0);

		uService.buy(2,1);
		uService.cancel(1,1);
//		uService.confirmTransfer(2,1);
//		uService.confirmReception(1,1);
	}*/

}
