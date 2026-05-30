package org.example.dao;

import org.example.model.Company;

import java.sql.Timestamp;
import java.util.List;

public interface CompanyDaoDb {

    List<Company> findAll();

    void updateLastSyncedAt(Long companyId, Timestamp syncedAt);

}
