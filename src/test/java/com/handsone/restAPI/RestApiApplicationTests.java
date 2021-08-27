package com.handsone.restAPI;

import org.junit.jupiter.api.Test;

import java.util.*;

//@SpringBootTest
class RestApiApplicationTests {
	@Test
	void test2() {
	}

	static class User{
		String name;
		List<String> hobs = new ArrayList<>();
		List<String> intro = new ArrayList<>();

		public String getName() {
			return name;
		}

		public List<String> getHobs() {
			return hobs;
		}

		public List<String> getIntro() {
			return intro;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setHobs(List<String> hobs) {
			this.hobs = hobs;
		}

		public void setIntro(List<String> intro) {
			this.intro = intro;
		}
	}
}
