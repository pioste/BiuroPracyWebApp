package com.biuropracy.demo.repository;

import com.biuropracy.demo.DTO.JobOfferDTO;
import com.biuropracy.demo.model.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface JobOfferRepository extends JpaRepository<JobOffer, Integer> {

    JobOffer findByEmployerIdEmployer(Integer id);

    /**
     * wyświetlanie pofiltrowanych ofert pracy
     * @param title
     * @param category
     * @param location
     * @param contractType
     * @param workingTime
     * @param positionLevel
     * @param monthlyPay
     * @param categorySalary
     * @return
     */
    @Query("Select new com.biuropracy.demo.DTO.JobOfferDTO(j.idJobOffer, j.title, j.location, j.category, j.categorySalary, e.companyName, j.monthlyPay) from JobOffer j, Employer e "
            + "where e.idEmployer = j.employer "
            + "and (j.title like lower(concat('%', :title,'%') ) or :title is null or :title = '')"
            + "and (j.category = :category or :category is null or :category = '')"
            + "and (j.location = :location or :location is null or :location = '')"
            + "and (j.contractType = :contractType or :contractType is null or :contractType = '')"
            + "and (j.workingTime = :workingTime or :workingTime is null or :workingTime = '')"
            + "and (j.positionLevel = :positionLevel or :positionLevel is null or :positionLevel = '')"
            + "and (j.monthlyPay >= :monthlyPay or :monthlyPay is null or :monthlyPay = '')"
            + "and (j.categorySalary = :categorySalary or :categorySalary is null or :categorySalary = '')"
    )
    List<JobOfferDTO> getJobOfferFiltered(@Param("title") String title, @Param("category") String category, @Param("location") String location, @Param("contractType") String contractType,
                                          @Param("workingTime") String workingTime, @Param("positionLevel") String positionLevel, @Param("monthlyPay") Integer monthlyPay,
                                          @Param("categorySalary") String categorySalary);

    /**
     * wyświetlanie wybranej oferty pracy po id
     * @param idJobOffer
     * @return
     */
    @Query("Select new com.biuropracy.demo.DTO.JobOfferDTO(e.idEmployer, j.idJobOffer, j.title, j.location, j.description, j.contact, j.category, j.categorySalary, e.companyName, j.contractType, j.workingTime, j.monthlyPay,  j.positionLevel) from JobOffer j, Employer e "
            + "where e.idEmployer = j.employer and j.idJobOffer = :idJobOffer")
    List<JobOfferDTO> getSelectedJobOffer(@Param("idJobOffer") Integer idJobOffer);

    /**
     * wyświetlanie oferty pracy po id pracodawcy
     * @param idEmployer
     * @return
     */
    @Query("Select new com.biuropracy.demo.DTO.JobOfferDTO(e.idEmployer, j.idJobOffer, j.title, j.location, j.description, j.contact, j.category, j.categorySalary, e.companyName, j.contractType, j.workingTime, j.monthlyPay,  j.positionLevel) from JobOffer j, Employer e "
            + "where e.idEmployer = j.employer and j.employer.idEmployer = :idEmployer")
    List<JobOfferDTO> getSelectedJobOfferByEmployerId(@Param("idEmployer") Integer idEmployer);

    /**
     * wyświtlanie pofiltrowanych ofert pracy dla admina
     * @param title
     * @param category
     * @param location
     * @param contractType
     * @param workingTime
     * @param positionLevel
     * @param monthlyPay
     * @param categorySalary
     * @return
     */
    @Query("Select new com.biuropracy.demo.DTO.JobOfferDTO(e.idEmployer, j.idJobOffer, j.title, j.location, j.description, j.contact, j.category, j.categorySalary, e.companyName, j.contractType, j.workingTime, j.monthlyPay,  j.positionLevel) from JobOffer j, Employer e "
            + "where e.idEmployer = j.employer "
            + "and (j.title like lower(concat('%', :title,'%') ) or :title is null or :title = '')"
            + "and (j.category = :category or :category is null or :category = '')"
            + "and (j.location = :location or :location is null or :location = '')"
            + "and (j.contractType = :contractType or :contractType is null or :contractType = '')"
            + "and (j.workingTime = :workingTime or :workingTime is null or :workingTime = '')"
            + "and (j.positionLevel = :positionLevel or :positionLevel is null or :positionLevel = '')"
            + "and (j.monthlyPay >= :monthlyPay or :monthlyPay is null or :monthlyPay = '')"
            + "and (j.categorySalary = :categorySalary or :categorySalary is null or :categorySalary = '')"
    )
    List<JobOfferDTO> getJobOfferFilteredAdmin(@Param("title") String title, @Param("category") String category, @Param("location") String location, @Param("contractType") String contractType,
                                               @Param("workingTime") String workingTime, @Param("positionLevel") String positionLevel, @Param("monthlyPay") Integer monthlyPay,
                                               @Param("categorySalary") String categorySalary);

}
