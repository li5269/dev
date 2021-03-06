/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf;

import jsf.AbstractFacade;
import entity.Reply;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author 96007
 */
@Stateless
public class ReplyFacade extends AbstractFacade<Reply> {

    @PersistenceContext(unitName = "AluntanPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ReplyFacade() {
        super(Reply.class);
    }
    
}
