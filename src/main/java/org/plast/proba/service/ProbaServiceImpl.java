package org.plast.proba.service;

import org.plast.proba.domain.ProbaFactory;
import org.plast.proba.domain.model.ConfirmPointRequest;
import org.plast.proba.domain.model.PointState;
import org.plast.proba.domain.pojo.Tochka;
import org.plast.proba.domain.pojo.ProbaToTochka;
import org.plast.proba.domain.pojo.User;
import org.plast.proba.domain.pojo.UserProba;
import org.plast.proba.repository.TochkaRepository;
import org.plast.proba.repository.ProbaRepository;
import org.plast.proba.repository.ProbaToTochkaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProbaServiceImpl implements ProbaService {

    @Autowired
    private ProbaRepository probaRepository;

    @Autowired
    private TochkaRepository tochkaRepository;

    @Autowired
    private ProbaToTochkaRepository probaToTochkaRepository;

    @Override
    public List<UserProba> probaListByUserId(long id) {
        return probaRepository.findByUserId(id);
    }

    @Override
    public UserProba probaById(long id) {
        return probaRepository.getOne(id);
    }

    @Override
    public List<PointState> probaWithPointsById(long id) {
        UserProba userProba = probaRepository.getOne(id);
        List<ProbaToTochka> proba = probaToTochkaRepository.findByUserProba(userProba);

        List<Tochka> tochkaList = tochkaRepository.findByRank(userProba.getRank());
        List<PointState> pointStateList = tochkaList.stream()
                .map(tochka -> ProbaFactory.createProbaState(proba, tochka))
                .collect(Collectors.toList());
        return pointStateList;
    }

    @Override
    public void confirmPoint(ConfirmPointRequest confirm, User loggedInUser) {
        UserProba userProba = probaRepository.findById(confirm.getProbaId()).get();
        Tochka tochka = tochkaRepository.findById((int) confirm.getPointId()).get();
        ProbaToTochka probaToTochka = probaToTochkaRepository.findByUserProbaAndTochka(userProba, tochka);
        if (probaToTochka == null)
            probaToTochka = new ProbaToTochka();

        probaToTochka.setTochka(tochka);
        probaToTochka.setUserProba(userProba);
        probaToTochka.setConfirmUser("USP".equals(loggedInUser.getUlad()) ? loggedInUser : null);
        probaToTochka.setConfirmDate(confirm.getConfirmDate());
        probaToTochkaRepository.save(probaToTochka);
    }

}
