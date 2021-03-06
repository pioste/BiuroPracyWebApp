package com.biuropracy.demo.repository;

import com.biuropracy.demo.DTO.EmployerUserDTO;
import com.biuropracy.demo.model.Employer;
import com.biuropracy.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Integer> {

    List<Employer> findByUserIdUser(Integer user);

    Employer findEmployerByUser_IdUser(Integer id);

    /**
     * zapytanie wyświetlające informacje w profilu pracodawcy
     * @param idEmployer
     * @return
     */
    @Query("select new com.biuropracy.demo.DTO.EmployerUserDTO(u.idUser, u.name, u.lastName, u.email, e.idEmployer, e.companyName, e.phoneNumber, e.companyEmail, e.webLink, e.address, e.description, u.userPhone) from User u, Employer e "
            + "where u.idUser = e.user and e.idEmployer = :idEmployer")
    List<EmployerUserDTO> getEmployerUserByIdEmpl(@Param("idEmployer") Integer idEmployer);

    /**
     * wyszukiwanie pracodawcy po nazwie firmy
     * @param companyName
     * @return
     */
    @Query("select new com.biuropracy.demo.DTO.EmployerUserDTO(e.idEmployer, e.companyName, e.webLink, e.address) from Employer e "
            + "where e.companyName like lower(concat('%', :companyName,'%') ) or :companyName is null or :companyName = '' ")
    List<EmployerUserDTO> getEmployerFiltered(@Param("companyName") String companyName);
}

