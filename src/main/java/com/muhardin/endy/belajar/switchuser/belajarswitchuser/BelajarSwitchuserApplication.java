package com.muhardin.endy.belajar.switchuser.belajarswitchuser;

import com.github.javafaker.Faker;
import com.muhardin.endy.belajar.switchuser.belajarswitchuser.dao.PenggunaDao;
import com.muhardin.endy.belajar.switchuser.belajarswitchuser.dao.TransaksiDao;
import com.muhardin.endy.belajar.switchuser.belajarswitchuser.entity.Pengguna;
import com.muhardin.endy.belajar.switchuser.belajarswitchuser.entity.Transaksi;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class BelajarSwitchuserApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BelajarSwitchuserApplication.class, args);
	}

	@Autowired private PenggunaDao penggunaDao;
	@Autowired private TransaksiDao transaksiDao;

	@Bean
	public LayoutDialect layoutDialect() {
		return new LayoutDialect();
	}

	@Override
	public void run(String... args) throws Exception {
		penggunaDao.findAll().forEach(this::generateTransaction);
	}

	private void generateTransaction(Pengguna pengguna) {
		Faker faker = new Faker() ;
		for (int i = 0; i < 20; i++) {
			Transaksi t = new Transaksi();
			t.setPengguna(pengguna);
			t.setKeterangan("Transaksi "+pengguna.getNama()+" #"+ (i+1));
			t.setWaktuTransaksi(faker.date().past(5, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
			t.setNilai(new BigDecimal(faker.number().numberBetween(10000,100000000)));
			transaksiDao.save(t);
		}
	}
}
