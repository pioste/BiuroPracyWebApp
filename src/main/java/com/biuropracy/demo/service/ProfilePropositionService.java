package com.biuropracy.demo.service;

import com.biuropracy.demo.model.JobOffer;
import com.biuropracy.demo.model.ProfileProposition;
import com.biuropracy.demo.model.User;
import com.biuropracy.demo.repository.ProfilePropositionRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfilePropositionService {

    @Autowired
    ProfilePropositionRepository profilePropositionRepository;
    @Autowired
    private EntityManagerService entityManagerService;

    public ProfileProposition findByUserId(Integer id){
        return profilePropositionRepository.findByUserIdUser(id);
    }

    /**
     * aktualizowanie decyzji zgłoszenia o pracę
     * @param profileProposition
     * @return
     */
    public ProfileProposition updateProfileProp(ProfileProposition profileProposition) {
        Optional<ProfileProposition> profilePropOpt = profilePropositionRepository.findById(profileProposition.getIdProfileProposition());
        if (profilePropOpt.isPresent()) {
            ProfileProposition newProfileProp = profilePropOpt.get();
            newProfileProp.setDecision(profileProposition.getDecision());

            newProfileProp = profilePropositionRepository.save(newProfileProp);
            return newProfileProp;
        } else {
            profileProposition = profilePropositionRepository.save(profileProposition);
            return profileProposition;
        }
    }

    /**
     * aktualizowanie zgłoszenia o pracę
     * @param profileProposition
     * @return
     */
    public ProfileProposition updateProfilePropAdmin(ProfileProposition profileProposition) {
        Optional<ProfileProposition> profilePropOpt = profilePropositionRepository.findById(profileProposition.getIdProfileProposition());
        if (profilePropOpt.isPresent()) {
            ProfileProposition newProfileProp = profilePropOpt.get();
            newProfileProp.setSubstantiation(profileProposition.getSubstantiation());
            newProfileProp.setContactType(profileProposition.getContactType());
            newProfileProp.setDecision(profileProposition.getDecision());

            newProfileProp = profilePropositionRepository.save(newProfileProp);
            return newProfileProp;
        } else {
            profileProposition = profilePropositionRepository.save(profileProposition);
            return profileProposition;
        }
    }

    /**
     * tworzenie złoszenia o pracę
     * @param profileProposition
     * @param user
     * @param jobOffer
     * @return
     */
    public ProfileProposition createProfileProp(ProfileProposition profileProposition, User user, JobOffer jobOffer){
        profileProposition.setUser(user);
        profileProposition.setDecision("Nierozpatrzona");
        profileProposition.setJobOffer(jobOffer);
        profileProposition=profilePropositionRepository.save(profileProposition);
        return profileProposition;
    }

    /**
     * uswanie zgłoszenia o pracę
     * @param id
     * @throws RuntimeException
     */
    public void deleteProfileProp(Integer id) throws RuntimeException{
        Optional<ProfileProposition> profilePropOpt = profilePropositionRepository.findById(id);
        if (profilePropOpt.isPresent()) {
            SessionFactory sessionFactory = entityManagerService.getSessionFactory();
            Session session = sessionFactory.openSession();
            String query = "Delete from profile_proposition where idprofile_proposition ="+id;
            session.doWork(connection -> connection.prepareStatement(query).execute());
            session.close();
        } else {
            throw new RuntimeException("Brak ProfileProposition o tym id");
        }
    }


}
