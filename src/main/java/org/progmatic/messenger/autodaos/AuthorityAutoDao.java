/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.progmatic.messenger.autodaos;

import org.progmatic.messenger.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author peti
 */
public interface AuthorityAutoDao extends JpaRepository<Authority, String>{
    public Authority findByName(String name);
}
