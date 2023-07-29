package com.nexuswawe.serverwownews.Core;

import java.io.IOException;

public interface IinitialFirabase extends IReadAdminSdk {
    void BuildFireBaseRDb() throws IOException;
    void GetFirebaseDatabaseInstance();
}
