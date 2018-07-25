package org.poc.app;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.poc.app.resources.FileApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestContextLoad {

	@Autowired
	private FileApi fileApi;
	
	@Test
	public void contextLoad(){
		assertThat(fileApi).isNotNull();
	}
}
