package com.muhardin.endy.belajar.switchuser.belajarswitchuser.dao;

import com.muhardin.endy.belajar.switchuser.belajarswitchuser.entity.AuditLog;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogDao extends PagingAndSortingRepository<AuditLog, String> {
}
