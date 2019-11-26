package br.ufg.labtime.notifica.dto.user;

import br.ufg.labtime.notifica.model.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserSpec {
    public static Specification<User> getAll(UserSearch search) {
        return (root, query, builder) -> {

            List<Predicate> predicatesAnd = new ArrayList<>();

            if (Optional.ofNullable(search.getId()).isPresent())
                predicatesAnd.add(builder.equal(root.get("id"), search.getId()));

            if (Optional.ofNullable(search.getEmail()).isPresent())
                predicatesAnd.add(builder.equal(root.get("email"), search.getEmail()));

            if (Optional.ofNullable(search.getName()).isPresent())
                predicatesAnd.add(builder.equal(root.get("name"), search.getName()));

            if (Optional.ofNullable(search.getSituation()).isPresent())
                predicatesAnd.add(builder.equal(root.get("situation"), search.getSituation()));

            if (predicatesAnd.isEmpty())
                return null;

            return builder.and(predicatesAnd.toArray(new Predicate[predicatesAnd.size()]));
        };
    }
}
