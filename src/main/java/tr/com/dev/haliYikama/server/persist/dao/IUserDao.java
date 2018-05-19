package tr.com.dev.haliYikama.server.persist.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tr.com.dev.haliYikama.server.persist.models.User;

/**
 * Created by ramazancesur on 5/19/18.
 */
@Repository("userDao")
public interface IUserDao extends JpaRepository<User, Long> {
    User findByKullaniciAdi(String kullaniciAdi);
}