package com.muhardin.endy.belajar.switchuser.belajarswitchuser.dao;

import com.muhardin.endy.belajar.switchuser.belajarswitchuser.entity.Pengguna;
import com.muhardin.endy.belajar.switchuser.belajarswitchuser.entity.Transaksi;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransaksiDao extends PagingAndSortingRepository<Transaksi, String> {
    Iterable<Transaksi> findByPengguna(Pengguna p);
}
