import javabook.model.entity.Member;
import javabook.model.entity.Team;
import org.hibernate.annotations.Synchronize;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        logic(em);
        tx.commit();
        try {

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    private static void teamSave(EntityManager em) {
        // 팀1 저장
        Team team1 = new Team("team1", "팀1");
        em.persist(team1);

        //회원1 저장
        Member member1 = new Member("member1", "회원1");
        member1.setTeam(team1);
        em.persist(member1);

        Member member2 = new Member("member2", "회원2");
        member2.setTeam(team1);
        em.persist(member2);
    }

    private static void logic(EntityManager em) {
//        teamSave(em);
//        biDirection(em);
//        queryLogicJoin(em);
//        updatreRelation(em);
//        deleteRelation(em);
        testSaveNonOwner(em);
    }
    private static void testSaveNonOwner(EntityManager em) {

        //회원1 저장
        Member member1 = new Member("member1", "회원1");
        em.persist(member1);

        Member member2 = new Member("member2", "회원2");
        em.persist(member2);

        Team team1 = new Team("team1", "팀1");
        team1.getMembers().add(member1);
        team1.getMembers().add(member2);
        em.persist(team1);

    }
    private static void biDirection(EntityManager em) {
        Team team = em.find(Team.class, "team1");
        List<Member> members = team.getMembers();
        System.out.println("=================");
        System.out.println(members.toString() + members.size());
        for (Member member : members) {
            System.out.println("member.username = " + member.getUsername());
        }
        System.out.println("=================");
    }

    private static void deleteRelation(EntityManager em) {
        Member member1 = em.find(Member.class, "member1");
        member1.setTeam(null);
    }

    private static void queryLogicJoin(EntityManager em) {
        String jpql = "select m from Member m join m.team t where t.name=:teamName";
        final List<Member> resultList = em.createQuery(jpql, Member.class)
                .setParameter("teamName", "팀1")
                .getResultList();
        for (Member member : resultList) {
            System.out.println("[query] member.username=" + member.getUsername());
        }
    }

    private static void updatreRelation (EntityManager em) {
        // 새로운 팀
        Team team2 = new Team("team2", "팀2");
        em.persist(team2);

        Member member = em.find(Member.class, "member1");
        member.setTeam(team2);
    }
}
