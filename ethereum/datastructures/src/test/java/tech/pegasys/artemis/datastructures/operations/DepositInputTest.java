/*
 * Copyright 2019 ConsenSys AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package tech.pegasys.artemis.datastructures.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import net.consensys.cava.bytes.Bytes;
import net.consensys.cava.bytes.Bytes32;
import net.consensys.cava.bytes.Bytes48;
import org.junit.jupiter.api.Test;

class DepositInputTest {

  private Bytes48 pubkey = Bytes48.random();
  private Bytes32 withdrawalCredentials = Bytes32.random();
  private BLSSignature proofOfPossession = new BLSSignature(Bytes48.random(), Bytes48.random());

  private DepositInput depositInput =
      new DepositInput(pubkey, withdrawalCredentials, proofOfPossession);

  @Test
  void equalsReturnsTrueWhenObjectsAreSame() {
    DepositInput testDepositInput = depositInput;

    assertEquals(depositInput, testDepositInput);
  }

  @Test
  void equalsReturnsTrueWhenObjectFieldsAreEqual() {
    DepositInput testDepositInput =
        new DepositInput(pubkey, withdrawalCredentials, proofOfPossession);

    assertEquals(depositInput, testDepositInput);
  }

  @Test
  void equalsReturnsFalseWhenPubkeysAreDifferent() {
    DepositInput testDepositInput =
        new DepositInput(pubkey.not(), withdrawalCredentials, proofOfPossession);

    assertNotEquals(depositInput, testDepositInput);
  }

  @Test
  void equalsReturnsFalseWhenWithdrawalCredentialsAreDifferent() {
    DepositInput testDepositInput =
        new DepositInput(pubkey, withdrawalCredentials.not(), proofOfPossession);

    assertNotEquals(depositInput, testDepositInput);
  }

  @Test
  void equalsReturnsFalseWhenProofsOfPosessionAreDifferent() {
    // Create copy of signature and reverse to ensure it is different.
    BLSSignature reverseProofOfPossession =
        new BLSSignature(proofOfPossession.getC1(), proofOfPossession.getC0());

    DepositInput testDepositInput =
        new DepositInput(pubkey, withdrawalCredentials, reverseProofOfPossession);

    assertNotEquals(depositInput, testDepositInput);
  }

  @Test
  void rountripSSZ() {
    Bytes sszDepositInputBytes = depositInput.toBytes();
    assertEquals(depositInput, DepositInput.fromBytes(sszDepositInputBytes));
  }
}