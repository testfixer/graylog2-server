/**
 * This file is part of Graylog.
 *
 * Graylog is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Graylog is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Graylog.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.graylog2.shared.users;

import org.graylog2.plugin.database.PersistedService;
import org.graylog2.plugin.database.users.User;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService extends PersistedService {
    @Nullable
    User load(String username);

    int delete(String username);

    User create();

    List<User> loadAll();

    /**
     * @deprecated If you <b>really</b> need the root user, use {@link UserService#getRootUser()} instead.
     */
    @Deprecated
    User getAdminUser();

    /**
     * Get the root user. The root user might not be present in all environments and there shouldn't really be
     * a need to explicitly refer to the root user. But if you really need it, here you go.
     * @return The root user, if present. An empty optional otherwise.
     */
    Optional<User> getRootUser();

    long count();

    Collection<User> loadAllForRole(Role role);

    Set<String> getRoleNames(User user);

    List<String> getPermissionsForUser(User user);

    Set<String> getUserPermissionsFromRoles(User user);

    void dissociateAllUsersFromRole(Role role);
}
