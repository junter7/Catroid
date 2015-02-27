/*
 * Catroid: An on-device visual programming system for Android devices
 * Copyright (C) 2010-2014 The Catrobat Team
 * (<http://developer.catrobat.org/credits>)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * An additional term exception under section 7 of the GNU Affero
 * General Public License, version 3, is available at
 * http://developer.catrobat.org/license_additional_term
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

import org.catrobat.catroid.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ExtendedActions;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.ui.fragment.FormulaEditorFragment;

import java.util.List;

public class ChangeVolumeByNBrick extends FormulaBrick implements OnClickListener {

	private static final long serialVersionUID = 1L;

	public ChangeVolumeByNBrick() {
		addAllowedBrickField(BrickField.VOLUME_CHANGE);
	}

	public ChangeVolumeByNBrick(double changeVolumeValue) {
		initializeBrickFields(new Formula(changeVolumeValue));
	}

	public ChangeVolumeByNBrick(Formula volume) {
		initializeBrickFields(volume);
	}

	private void initializeBrickFields(Formula volume) {
		addAllowedBrickField(BrickField.VOLUME_CHANGE);
		setFormulaWithBrickField(BrickField.VOLUME_CHANGE, volume);
	}

	@Override
	public int getRequiredResources() {
		return getFormulaWithBrickField(BrickField.VOLUME).getRequiredResources();
	}

	@Override
	public View getView(Context context, int brickId, BaseAdapter baseAdapter) {
		if (animationState) {
			return view;
		}

		view = View.inflate(context, R.layout.brick_change_volume_by, null);

		TextView edit = (TextView) view.findViewById(R.id.brick_change_volume_by_edit_text);
		getFormulaWithBrickField(BrickField.VOLUME_CHANGE).setTextFieldId(R.id.brick_change_volume_by_edit_text);
		getFormulaWithBrickField(BrickField.VOLUME_CHANGE).refreshTextField(view);

		edit.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View view) {
		if (!clickAllowed()) {
			return;
		}
		FormulaEditorFragment.showFragment(view, this, getFormulaWithBrickField(BrickField.VOLUME_CHANGE));
	}

	@Override
	public List<SequenceAction> addActionToSequence(Sprite sprite, SequenceAction sequence) {
		sequence.addAction(ExtendedActions.changeVolumeByN(sprite, getFormulaWithBrickField(BrickField.VOLUME_CHANGE)));
		return null;
	}
}
