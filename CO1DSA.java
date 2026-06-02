import java.util.*;

class CacheEntry {
    String domain;
    long exp;
    String record;

    CacheEntry(String domain, long exp, String record) {
        this.domain = domain;
        this.exp = exp;
        this.record = record;
    }

    @Override
    public String toString() {
        return domain + " -> " + exp;
    }
}

public class DnsCacheBST {

    // Domain lookup
    private Map<String, CacheEntry> byDomain = new HashMap<>();

    // Balanced BST (Red-Black Tree)
    // Expiration Time -> Set of Domains
    private TreeMap<Long, Set<String>> expiryTree =
            new TreeMap<>();

    // Insert new DNS record
    public void put(String domain,
                    long exp,
                    String record) {

        if (byDomain.containsKey(domain)) {
            renewTTL(domain, exp);
            return;
        }

        CacheEntry entry =
                new CacheEntry(domain, exp, record);

        byDomain.put(domain, entry);

        expiryTree
                .computeIfAbsent(exp,
                        k -> new HashSet<>())
                .add(domain);
    }

    // Lookup
    public CacheEntry get(String domain) {
        return byDomain.get(domain);
    }

    // TTL Renewal
    public void renewTTL(String domain,
                         long newExp) {

        CacheEntry oldEntry =
                byDomain.get(domain);

        if (oldEntry == null)
            return;

        long oldExp = oldEntry.exp;

        Set<String> domains =
                expiryTree.get(oldExp);

        if (domains != null) {

            domains.remove(domain);

            if (domains.isEmpty()) {
                expiryTree.remove(oldExp);
            }
        }

        oldEntry.exp = newExp;

        expiryTree
                .computeIfAbsent(newExp,
                        k -> new HashSet<>())
                .add(domain);
    }

    // Find next entry to expire
    public CacheEntry nextToExpire() {

        if (expiryTree.isEmpty())
            return null;

        long exp =
                expiryTree.firstKey();

        String domain =
                expiryTree.get(exp)
                          .iterator()
                          .next();

        return byDomain.get(domain);
    }

    // Batch eviction
    public List<String> evictExpired(long now) {

        List<String> evicted =
                new ArrayList<>();

        while (!expiryTree.isEmpty()) {

            long earliest =
                    expiryTree.firstKey();

            if (earliest > now)
                break;

            Set<String> domains =
                    expiryTree.remove(earliest);

            for (String domain : domains) {

                byDomain.remove(domain);

                evicted.add(domain);
            }
        }

        return evicted;
    }

    public void displayCache() {

        System.out.println("\nCurrent Cache:");

        for (CacheEntry entry : byDomain.values()) {
            System.out.println(entry);
        }
    }

    public static void main(String[] args) {

        DnsCacheBST cache =
                new DnsCacheBST();

        cache.put("google.com",
                1700000060,
                "142.250.183.14");

        cache.put("github.com",
                1700000045,
                "140.82.121.4");

        cache.put("slack.com",
                1700000090,
                "18.216.31.54");

        cache.put("kl.ac.in",
                1700000030,
                "103.10.4.22");

        cache.put("jio.com",
                1700000075,
                "49.44.10.1");

        cache.put("hdfc.in",
                1700000020,
                "13.126.88.12");

        cache.put("amazon.in",
                1700000055,
                "176.32.103.205");

        cache.put("netflix.com",
                1700000040,
                "52.89.124.203");

        cache.displayCache();

        System.out.println("\nNext To Expire:");
        System.out.println(cache.nextToExpire());

        long now = 1700000030;

        System.out.println(
                "\nExpired Entries at "
                        + now + ":");

        List<String> evicted =
                cache.evictExpired(now);

        System.out.println(evicted);

        cache.displayCache();

        System.out.println(
                "\nRenewing TTL of github.com");

        cache.renewTTL(
                "github.com",
                1700000200);

        cache.displayCache();
    }
}