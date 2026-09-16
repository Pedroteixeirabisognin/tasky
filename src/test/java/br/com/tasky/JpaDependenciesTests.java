package br.com.tasky;

import jakarta.persistence.spi.PersistenceProviderResolverHolder;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import static org.assertj.core.api.Assertions.assertThat;

class JpaDependenciesTests {

    @Test
    void springDataJpaAndHibernateProviderAreAvailable() {
        assertThat(Repository.class).isAssignableFrom(JpaRepository.class);
        assertThat(PersistenceProviderResolverHolder.getPersistenceProviderResolver()
                .getPersistenceProviders())
                .anyMatch(HibernatePersistenceProvider.class::isInstance);
    }
}
