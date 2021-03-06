
/***************************************************************************
 *   Copyright 2006-2014 by Christian Ihle                                 *
 *   contact@kouchat.net                                                   *
 *                                                                         *
 *   This file is part of KouChat.                                         *
 *                                                                         *
 *   KouChat is free software; you can redistribute it and/or modify       *
 *   it under the terms of the GNU Lesser General Public License as        *
 *   published by the Free Software Foundation, either version 3 of        *
 *   the License, or (at your option) any later version.                   *
 *                                                                         *
 *   KouChat is distributed in the hope that it will be useful,            *
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU      *
 *   Lesser General Public License for more details.                       *
 *                                                                         *
 *   You should have received a copy of the GNU Lesser General Public      *
 *   License along with KouChat.                                           *
 *   If not, see <http://www.gnu.org/licenses/>.                           *
 ***************************************************************************/

package net.usikkert.kouchat.testclient;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Stores and queries messages.
 *
 * @author Christian Ihle
 */
public class TestClientMessageReceiver {

    private final List<String> receivedMessages;
    private final Pattern messagePattern;

    public TestClientMessageReceiver() {
        receivedMessages = new ArrayList<String>();
        messagePattern = Pattern.compile("\\[\\d{2}:\\d{2}:\\d{2}\\] <(\\w+)>: (.+)");
    }

    public void addMessage(final String message) {
        receivedMessages.add(message);
    }

    /**
     * Checks if the specified message has arrived from the specified user.
     *
     * <p>Expects messages in this format: <code>[14:29:21] &lt;Christian&gt;: hello there</code>.</p>
     *
     * <p>Only the actual nick name and the actual message is checked. The rest is ignored.</p>
     *
     * @param nickName The nick name of the user who sent the message.
     * @param message The message the user sent.
     * @return If the message has arrived.
     */
    public boolean gotMessage(final String nickName, final String message) {
        for (final String receivedMessage : receivedMessages) {
            final Matcher matcher = messagePattern.matcher(receivedMessage);

            if (matcher.matches()) {
                final String nickNameMatch = matcher.group(1);
                final String messageMatch = matcher.group(2);

                if (nickNameMatch.equals(nickName) && messageMatch.equals(message)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean gotAnyMessages() {
        return !receivedMessages.isEmpty();
    }

    public List<String> getMessages() {
        return new ArrayList<String>(receivedMessages);
    }

    public void resetMessages() {
        receivedMessages.clear();
    }
}
