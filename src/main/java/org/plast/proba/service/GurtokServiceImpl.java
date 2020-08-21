package org.plast.proba.service;

import org.plast.proba.domain.pojo.Gurtok;
import org.plast.proba.repository.GurtokRepository;
import org.plast.proba.repository.ProbaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GurtokServiceImpl implements GurtokService {

    @Autowired
    private GurtokRepository gurtokRepository;

    @Override
    public Gurtok getGurtokById(Long gurtokId) {
        return gurtokRepository.findById(gurtokId).get();
    }
}
