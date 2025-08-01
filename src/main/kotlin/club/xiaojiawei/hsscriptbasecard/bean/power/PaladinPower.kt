package club.xiaojiawei.hsscriptbasecard.bean.power

import club.xiaojiawei.hsscriptcardsdk.CardAction
import club.xiaojiawei.hsscriptcardsdk.bean.Card
import club.xiaojiawei.hsscriptcardsdk.bean.Player
import club.xiaojiawei.hsscriptcardsdk.bean.PowerAction
import club.xiaojiawei.hsscriptcardsdk.bean.TEST_CARD_ACTION
import club.xiaojiawei.hsscriptbasecard.bean.abs.ClickPower
import club.xiaojiawei.hsscriptcardsdk.enums.CardTypeEnum
import club.xiaojiawei.hsscriptcardsdk.status.WAR
import club.xiaojiawei.hsscriptcardsdk.bean.War

/**
 * 圣骑士技能
 * @author 肖嘉威
 * @date 2024/9/22 18:13
 */
private val cardIds = arrayOf<String>(
    "HERO_04%bp",
    "HERO_04%hp",
    "CS2_101_H%",
    "RLK_Prologue_Arthas_001p",
)

class PaladinPower : ClickPower() {

    override fun generatePowerActions(war: War, player: Player): List<PowerAction> {
        if (war.me.playArea.isFull) return emptyList()
        return listOf(
            PowerAction(
                { newWar ->
                    newWar.me.playArea.power?.action?.power()
                }, { newWar ->
                    spendSelfCost(newWar)
                    val card = Card(TEST_CARD_ACTION).apply {
                        health = 1
                        atc = 1
                        cost = 1
                        cardType = CardTypeEnum.MINION
                        isExhausted = true
                        this.entityId = newWar.incrementMaxEntityId()
                    }
                    newWar.addCard(card, newWar.me.playArea)
                    findSelf(newWar)?.isExhausted = true
                }, belongCard)
        )
    }

    override fun getCardId(): Array<String> {
        return cardIds
    }

    override fun execPower(): Boolean {
        if (WAR.me.playArea.isFull) return false
        return super.execPower()
    }

    override fun createNewInstance(): CardAction {
        return PaladinPower()
    }
}