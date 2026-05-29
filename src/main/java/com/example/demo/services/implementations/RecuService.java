package com.example.demo.services.implementations;

import com.example.demo.dto.RecuDTO;
import com.example.demo.enties.Paiement;
import com.example.demo.enties.Recu;
import com.example.demo.mappers.RecuMapper;
import com.example.demo.repositories.PaiementRepos;
import com.example.demo.repositories.RecuRepos;
import com.example.demo.services.interfaces.RecuInterface;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecuService implements RecuInterface {

    private final RecuRepos recuRepos;
    private final PaiementRepos paiementRepos;
    private final RecuMapper mapper;

    public RecuService(RecuRepos recuRepos,
                       PaiementRepos paiementRepos,
                       RecuMapper mapper) {
        this.recuRepos = recuRepos;
        this.paiementRepos = paiementRepos;
        this.mapper = mapper;
    }

    @Override
    public RecuDTO create(RecuDTO dto) {

        Paiement paiement = paiementRepos.findById(dto.getIdPaiement())
                .orElseThrow(() -> new RuntimeException("Paiement non trouvé"));

        boolean existe = recuRepos.findAll()
                .stream()
                .anyMatch(r -> r.getIdPaiement() != null &&
                        r.getIdPaiement().getIdPaiement().equals(dto.getIdPaiement()));

        if (existe) {
            throw new RuntimeException("Reçu déjà généré pour ce paiement");
        }

        Recu recu = mapper.toEntity(dto);
        recu.setIdPaiement(paiement);

        return mapper.toDTO(recuRepos.save(recu));
    }

    @Override
    public RecuDTO update(Integer id, RecuDTO dto) {

        Recu recu = recuRepos.findById(id)
                .orElseThrow(() -> new RuntimeException("Reçu non trouvé"));

        recu.setContenu(dto.getContenu());
        recu.setDateEmission(dto.getDateEmission());

        return mapper.toDTO(recuRepos.save(recu));
    }

    @Override
    public RecuDTO getById(Integer id) {
        return mapper.toDTO(
                recuRepos.findById(id)
                        .orElseThrow(() -> new RuntimeException("Reçu non trouvé"))
        );
    }

    @Override
    public List<RecuDTO> getAll() {
        return recuRepos.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {

        Recu recu = recuRepos.findById(id)
                .orElseThrow(() -> new RuntimeException("Reçu non trouvé"));

        recuRepos.delete(recu);
    }
}