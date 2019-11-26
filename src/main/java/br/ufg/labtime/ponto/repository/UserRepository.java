package br.ufg.labtime.ponto.repository;

import br.ufg.labtime.ponto.model.User;
import br.ufg.labtime.ponto.model.type.Situation;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long>, JpaSpecificationExecutor<User> {

    User findByEmail(String email);

    Page<User> findAllBySituationEquals(Pageable page, @NonNull Situation situation);

    @Transactional
    long countAllByAdmTrue();

    @Transactional
    @Modifying
    @Query("update User u set u.adm = :adm WHERE u.id = :id")
    void changeAdm(@Param("id") Long id, @Param("adm") boolean adm);

    @Transactional
    @Modifying
    @Query("update User u set u.situation = :situation WHERE u.id = :id")
    void changeSituation(@Param("id") long id, @Param("situation") Situation situation);


}
