package com.muhardin.endy.belajar.switchuser.belajarswitchuser.controller;

import com.muhardin.endy.belajar.switchuser.belajarswitchuser.dao.PenggunaDao;
import com.muhardin.endy.belajar.switchuser.belajarswitchuser.dao.TransaksiDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class TransaksiController {

    @Autowired
    private TransaksiDao transaksiDao;

    @Autowired private PenggunaDao penggunaDao;

    @GetMapping("/transaksi/list")
    public ModelMap daftarTransaksi(Authentication currentUser) {
        ModelMap mm = new ModelMap();

        penggunaDao.findByUsername(currentUser.getName())
                .ifPresent(p-> mm.addAttribute(
                        "daftarTransaksi",
                        transaksiDao.findByPengguna(p)));

        return mm;
    }
}
