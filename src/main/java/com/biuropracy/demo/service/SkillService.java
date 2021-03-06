package com.biuropracy.demo.service;

import com.biuropracy.demo.model.Skill;
import com.biuropracy.demo.model.User;
import com.biuropracy.demo.repository.SkillRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SkillService {

    @Autowired
    SkillRepository skillRepository;
    @Autowired
    private EntityManagerService entityManagerService;

    public List<Skill> findSkillByUserId(Integer user) {
        return skillRepository.findByUserIdUser(user);
    }

    public Skill findByUserId(Integer id){
        return skillRepository.findSkillByUserIdUser(id);
    }

    /**
     * aktualizowanie doświadczenia zawodowego
     * @param skill
     * @return
     */
    public Skill updateSkill(Skill skill) {
        Optional<Skill> skillOpt = skillRepository.findById(skill.getIdSkill());
        if (skillOpt.isPresent()) {
            Skill newSkill = skillOpt.get();
            newSkill.setSkillName(skill.getSkillName());
            newSkill = skillRepository.save(newSkill);
            return newSkill;
        } else {
            skill = skillRepository.save(skill);
            return skill;
        }
    }

    /**
     * zapisywanie umiejętności do bazy
     * @param skill
     * @param user
     * @return
     */
    public Skill createSkill(Skill skill, User user) {
        skill.setUser(user);
        skill = skillRepository.save(skill);
        return skill;
    }

    /**
     * usuwanie umiejętności z bazy
     * @param id
     * @throws RuntimeException
     */
    public void deleteSkillById(Integer id) throws RuntimeException{
        Optional<Skill> skillOpt = skillRepository.findById(id);
        if (skillOpt.isPresent()) {
            SessionFactory sessionFactory = entityManagerService.getSessionFactory();
            Session session = sessionFactory.openSession();
            String query = "Delete from skill where id_skill ="+id;
            session.doWork(connection -> connection.prepareStatement(query).execute());
            session.close();
        } else {
            throw new RuntimeException("Brak skill o tym id");
        }
    }
}
