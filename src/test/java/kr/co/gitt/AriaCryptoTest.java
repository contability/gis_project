package kr.co.gitt;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import egovframework.rte.fdl.cryptography.EgovCryptoService;
import egovframework.rte.fdl.cryptography.EgovPasswordEncoder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath*:/kworks/spring/context-common.xml",
	"classpath*:/kworks/spring/context-profile.xml",
	"classpath*:/kworks/spring/context-properties.xml",
	"classpath*:/kworks/spring/context-crypto.xml"
})
public class AriaCryptoTest {
	
	@Resource(name="ARIACryptoService")
    EgovCryptoService cryptoService;

	@Test
	public void test() throws Exception {
		String str = "NONE";
		EgovPasswordEncoder encoder = new EgovPasswordEncoder();
		encoder.setAlgorithm("SHA-256");
		System.out.println("Digested Password : " + encoder.encryptPassword(str));
	}

}
