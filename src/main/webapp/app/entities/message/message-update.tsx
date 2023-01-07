import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMembership } from 'app/shared/model/membership.model';
import { getEntities as getMemberships } from 'app/entities/membership/membership.reducer';
import { IChannel } from 'app/shared/model/channel.model';
import { getEntities as getChannels } from 'app/entities/channel/channel.reducer';
import { IDirectMessage } from 'app/shared/model/direct-message.model';
import { getEntities as getDirectMessages } from 'app/entities/direct-message/direct-message.reducer';
import { IMessage } from 'app/shared/model/message.model';
import { getEntity, updateEntity, createEntity, reset } from './message.reducer';

export const MessageUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const memberships = useAppSelector(state => state.membership.entities);
  const channels = useAppSelector(state => state.channel.entities);
  const directMessages = useAppSelector(state => state.directMessage.entities);
  const messageEntity = useAppSelector(state => state.message.entity);
  const loading = useAppSelector(state => state.message.loading);
  const updating = useAppSelector(state => state.message.updating);
  const updateSuccess = useAppSelector(state => state.message.updateSuccess);

  const handleClose = () => {
    navigate('/message');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getMemberships({}));
    dispatch(getChannels({}));
    dispatch(getDirectMessages({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...messageEntity,
      ...values,
      membership: memberships.find(it => it.id.toString() === values.membership.toString()),
      membership: memberships.find(it => it.id.toString() === values.membership.toString()),
      channel: channels.find(it => it.id.toString() === values.channel.toString()),
      channel: channels.find(it => it.id.toString() === values.channel.toString()),
      directMessage: directMessages.find(it => it.id.toString() === values.directMessage.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...messageEntity,
          membership: messageEntity?.membership?.id,
          channel: messageEntity?.channel?.id,
          membership: messageEntity?.membership?.id,
          channel: messageEntity?.channel?.id,
          directMessage: messageEntity?.directMessage?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jiveApp.message.home.createOrEditLabel" data-cy="MessageCreateUpdateHeading">
            <Translate contentKey="jiveApp.message.home.createOrEditLabel">Create or edit a Message</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="message-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('jiveApp.message.text')} id="message-text" name="text" data-cy="text" type="text" />
              <ValidatedField
                id="message-membership"
                name="membership"
                data-cy="membership"
                label={translate('jiveApp.message.membership')}
                type="select"
              >
                <option value="" key="0" />
                {memberships
                  ? memberships.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="message-channel"
                name="channel"
                data-cy="channel"
                label={translate('jiveApp.message.channel')}
                type="select"
              >
                <option value="" key="0" />
                {channels
                  ? channels.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="message-membership"
                name="membership"
                data-cy="membership"
                label={translate('jiveApp.message.membership')}
                type="select"
              >
                <option value="" key="0" />
                {memberships
                  ? memberships.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="message-channel"
                name="channel"
                data-cy="channel"
                label={translate('jiveApp.message.channel')}
                type="select"
              >
                <option value="" key="0" />
                {channels
                  ? channels.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="message-directMessage"
                name="directMessage"
                data-cy="directMessage"
                label={translate('jiveApp.message.directMessage')}
                type="select"
              >
                <option value="" key="0" />
                {directMessages
                  ? directMessages.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/message" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default MessageUpdate;
